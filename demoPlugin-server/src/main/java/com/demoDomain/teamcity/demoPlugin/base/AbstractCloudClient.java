package com.demoDomain.teamcity.demoPlugin.base;

import com.intellij.openapi.diagnostic.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jetbrains.buildServer.clouds.*;

import com.demoDomain.teamcity.demoPlugin.base.beans.CloudImageDetails;

import com.demoDomain.teamcity.demoPlugin.base.connector.CloudApiConnector;

import com.demoDomain.teamcity.demoPlugin.base.connector.CloudAsyncTaskExecutor;

import com.demoDomain.teamcity.demoPlugin.base.errors.CloudErrorMap;

import com.demoDomain.teamcity.demoPlugin.base.errors.TypedCloudErrorInfo;

import com.demoDomain.teamcity.demoPlugin.base.errors.UpdatableCloudErrorProvider;

import com.demoDomain.teamcity.demoPlugin.base.tasks.UpdateInstancesTask;

import com.demoDomain.teamcity.demoPlugin.vidhi.errors.VmwareErrorMessages;

import jetbrains.buildServer.serverSide.AgentDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Sergey.Pak
 *         Date: 7/22/2014
 *         Time: 1:49 PM
 */
public abstract class AbstractCloudClient<G extends AbstractCloudInstance<T>, T extends AbstractCloudImage<G,D>, D extends CloudImageDetails>
  implements CloudClientEx, UpdatableCloudErrorProvider {

  private static final Logger LOG = Logger.getInstance(AbstractCloudClient.class.getName());
  protected final Map<String, T> myImageMap;
  protected final UpdatableCloudErrorProvider myErrorProvider;
  protected final CloudAsyncTaskExecutor myAsyncTaskExecutor;
  @NotNull protected final CloudApiConnector myApiConnector;
  protected final CloudClientParameters myParameters;
  private volatile boolean myIsInitialized = false;

  public AbstractCloudClient(@NotNull final CloudClientParameters params, @NotNull final CloudApiConnector apiConnector) {
    myParameters = params;
    myAsyncTaskExecutor = new CloudAsyncTaskExecutor("Async tasks for cloud " + params.getProfileDescription());
    myImageMap = new HashMap<String, T>();
    myErrorProvider = new CloudErrorMap(VmwareErrorMessages.getInstance());
    myApiConnector = apiConnector;
  }

  public boolean isInitialized() {
    return myIsInitialized;
  }


  public void dispose() {
    myAsyncTaskExecutor.dispose();
  }

  @NotNull
  public G startNewInstance(@NotNull final CloudImage baseImage, @NotNull final CloudInstanceUserData tag) throws QuotaException {
    final T image = (T)baseImage;
    return image.startNewInstance(tag);
  }

  public void restartInstance(@NotNull final CloudInstance baseInstance) {
    final G instance = (G)baseInstance;
    instance.getImage().restartInstance(instance);
  }

  public void terminateInstance(@NotNull final CloudInstance baseInstance) {
    final G instance = (G)baseInstance;
    instance.getImage().terminateInstance(instance);
  }

 /** @Deprecated
  public boolean canStartNewInstance(@NotNull final CloudImage baseImage) {
    final T image = (T)baseImage;
    return image.canStartNewInstance();
  }
**/
  /**@NotNull
  public CanStartNewInstanceResult canStartNewInstanceWithDetails(@NotNull final CloudImage baseImage) {
    final T image = (T)baseImage;
    return image.canStartNewInstanceWithDetails();
  }**/

  public void populateImagesData(@NotNull final Collection<D> imageDetails){
    populateImagesData(imageDetails, 60*1000, 60*1000);
  }

  public void populateImagesData(@NotNull final Collection<D> imageDetails, final long initialDelayMs, final long delayMs){
    for (D details : imageDetails) {
      T image = checkAndCreateImage(details);
      myImageMap.put(image.getName(), image);
    }
    final UpdateInstancesTask<G, T, ?> updateInstancesTask = createUpdateInstancesTask();
    if (updateInstancesTask == null) {
      return;
    }
    myAsyncTaskExecutor.submit("Populate images data", new Runnable() {
      public void run() {
        try {
          updateInstancesTask.run();
          myAsyncTaskExecutor.scheduleWithFixedDelay("Update instances", updateInstancesTask, initialDelayMs, delayMs, TimeUnit.MILLISECONDS);
        } finally {
          myIsInitialized = true;
          LOG.info("Cloud profile '" + myParameters.getProfileDescription() + "' initialized");
        }
      }
    });
  }

  protected abstract T checkAndCreateImage(@NotNull final D imageDetails);

  @NotNull
  protected abstract UpdateInstancesTask<G,T,?> createUpdateInstancesTask();

  @Nullable
  public abstract G findInstanceByAgent(@NotNull final AgentDescription agent);

  @Nullable
  public T findImageById(@NotNull final String imageId) throws CloudException {
    return myImageMap.get(imageId);
  }

  @NotNull
  public Collection<T> getImages() throws CloudException {
    return Collections.unmodifiableCollection(myImageMap.values());
  }

  public void updateErrors(final TypedCloudErrorInfo... errors) {
    myErrorProvider.updateErrors(errors);
  }

  @Nullable
  public CloudErrorInfo getErrorInfo() {
    return myErrorProvider.getErrorInfo();
  }
}
 
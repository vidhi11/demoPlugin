/*
 *
 *  * Copyright 2000-2014 JetBrains s.r.o.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.demoDomain.teamcity.demoPlugin.vidhi;

import org.jetbrains.annotations.NotNull;

import jetbrains.buildServer.clouds.CloudClientEx;
import jetbrains.buildServer.clouds.CloudClientFactory;
import jetbrains.buildServer.clouds.CloudClientParameters;
import jetbrains.buildServer.clouds.CloudState;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.clouds.CloudRegistrar;
import jetbrains.buildServer.serverSide.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

public class VMWareCloudClientFactory implements CloudClientFactory
{
	private PluginDescriptor pluginDescriptor;
	
	public VMWareCloudClientFactory(@NotNull CloudRegistrar cloudRegistrar){
		
		cloudRegistrar.registerCloudFactory(this);
	}
	
	
	@NotNull
	  public CloudClientEx createNewClient(@NotNull CloudState state, @NotNull CloudClientParameters params)
	  {
		return null;
	  }
	
	 @NotNull
	  public String getCloudCode()
	  {
		 return "VidCP";
	  }

	  /**
	   * Description to be shown on the web pages
	   * @return display name to be shown to the user
	   */
	  @NotNull
	  public String getDisplayName()
	  {
		  return "VidhiCloudProfile";
	  }


	  /**
	   * Properties editor jsp
	   * @return properties editor JSP file
	   * @since 5.1
	   */
	  @Nullable
	  public String getEditProfileUrl()
	  {
		  return pluginDescriptor.getPluginResourcesPath("vidhi-cloud-profile-setting-page.html");
	  }

	  /**
	   * Return initial values for form pareters.
	   * @return map of initial values
	   */
	  @NotNull
	  public Map<String,String> getInitialParameterValues()
	  {
		  return null;
	  }

	  /**
	   * Returns the properties processor instance (validator).
	   *
	   * @return properties processor
	   */
	  @NotNull
	  public PropertiesProcessor getPropertiesProcessor()
	  {
		  return null;
	  }

	  /**
	   * Checks it the agent could be an instance of one of the running profiles.
	   * This method is called to check weather it is needed to open connection
	   * for a cloud profiles of that type to check if the agent is started
	   * from a cloud profiles of that type.
	   * @param description agent info to check
	   * @return true if this agent could be an instance of that cloud type
	   */
	  public boolean canBeAgentOfType(@NotNull AgentDescription description)
	  {
		  return true;
	  }
}
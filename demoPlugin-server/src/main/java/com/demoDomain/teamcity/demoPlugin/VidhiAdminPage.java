package com.demoDomain.teamcity.demoPlugin;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import javax.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import jetbrains.buildServer.serverSide.auth.*;

public class VidhiAdminPage extends AdminPage {
  public VidhiAdminPage(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor) {
    super(pagePlaces);
    setPluginName("VidhiAdminPage");
    setIncludeUrl(descriptor.getPluginResourcesPath("/admin/VidhiAdminPage.jsp"));
    setTabTitle("VidhiAdminPage---------------");
    setPosition(PositionConstraint.after("clouds", "email", "jabber"));
    register();
  }
 
  @Override
  public boolean isAvailable(@NotNull HttpServletRequest request) {
    return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.CHANGE_SERVER_SETTINGS);
  }
 
  @NotNull
  public String getGroup() {
    return SERVER_RELATED_GROUP;
  }
}
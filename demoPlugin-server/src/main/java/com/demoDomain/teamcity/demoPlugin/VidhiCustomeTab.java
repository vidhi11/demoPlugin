package com.demoDomain.teamcity.demoPlugin;

import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import javax.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;

import java.util.Map;

public class VidhiCustomeTab  extends SimpleCustomTab{
	private PluginDescriptor myDescriptor;
  public VidhiCustomeTab(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor) {
	  
	  super(pagePlaces, PlaceId.EDIT_PROJECT_PAGE_TAB, "VidhiCustomTab----", descriptor.getPluginResourcesPath("VidhiCustomeTab.jsp"), "VidhiCustomTabTitle---------------");
	 
	    setPosition(PositionConstraint.after("clouds", "email", "jabber"));
	    register();
	 
  }
  
  
  @NotNull
  public PlaceId getPlaceId()
  {
	  return PlaceId.EDIT_PROJECT_PAGE_TAB;
  }


  public void fillModel(@NotNull
          Map<java.lang.String,java.lang.Object> model,
          @NotNull
          HttpServletRequest request)
  {
    // add your data here
  }
}
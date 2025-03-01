package org.example.finalproject.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.example.finalproject.bl.AdminBL;

@Path("/admin/{adminId}")
public class AdminsController {
    @Inject
    AdminBL adminBL;

    @PUT
    @Path("/request/{requestId}/approve")
    public void approveRequest(@PathParam("adminId") int adminId, @PathParam("requestId") int requestId) {
        adminBL.markRequestAsApproved(requestId, adminId);
    }

    @PUT
    @Path("/request/{requestId}/reject")
    public void rejectRequest(@PathParam("adminId") int adminId, @PathParam("requestId") int requestId) {
        adminBL.markRequestAsRejected(requestId, adminId);
    }

    @DELETE
    @Path("/menuitem/{menuItemId}")
    public void deleteMenuItem(@PathParam("adminId") int adminId, @PathParam("menuItemId") int menuItemId) {
        adminBL.deleteMenuItem(menuItemId, adminId);
    }
}

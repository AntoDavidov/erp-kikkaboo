package nl.fontys.s3.erp.business.exceptions;

public class PermissionDenied extends RuntimeException {
  public PermissionDenied(String action) {
    super("ACCESS DENIED: Do not have permission to " + action);
  }
}

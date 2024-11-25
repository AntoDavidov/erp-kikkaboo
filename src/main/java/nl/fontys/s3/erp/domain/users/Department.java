package nl.fontys.s3.erp.domain.users;

public enum Department {
    ACCOUNTING,
    PRODUCT,
    TRADE,
    E_COMMERCE;

    public static boolean isValidDepartment(final String department) {
        try {
            Department.valueOf(department);
            return true;
        }
        catch (final IllegalArgumentException e) {
            return false;
        }
    }
}

package net.texala.employee.enums;

public enum AddressType {
    HOME("home"),
    OFFICE("office");

    private final String label;

    AddressType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

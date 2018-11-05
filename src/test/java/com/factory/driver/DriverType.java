package com.factory.driver;

public enum DriverType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private String name;

    DriverType(String driverName) {
        this.name = driverName;
    }

    public String getName() {
        return name;
    }

    public static DriverType fromName(String name) throws DriverNotFoundException {
        for (DriverType value : DriverType.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new DriverNotFoundException(DriverNotFoundException.NO_DRIVER);
    }
}

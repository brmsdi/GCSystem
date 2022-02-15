package system.gc.utils;

public enum TypeUserEnum {
    EMPLOYEE(0),
    LESSEE(1);

    private int code;

    TypeUserEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TypeUserEnum valueOf(int code) {
        for (TypeUserEnum typeUserEnum : TypeUserEnum.values()) {
            if (code == typeUserEnum.getCode()) {
                return typeUserEnum;
            }
        }
        throw new IllegalArgumentException("Tipo invalido");
    }
}

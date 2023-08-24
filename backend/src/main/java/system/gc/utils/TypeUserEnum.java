package system.gc.utils;

import lombok.Getter;

import static system.gc.utils.TextUtils.TYPE_INVALID;

@Getter
public enum TypeUserEnum {
    EMPLOYEE(0),
    LESSEE(1);

    private final int code;

    TypeUserEnum(int code) {
        this.code = code;
    }

    public static TypeUserEnum valueOf(int code) {
        for (TypeUserEnum typeUserEnum : TypeUserEnum.values()) {
            if (code == typeUserEnum.getCode()) {
                return typeUserEnum;
            }
        }
        throw new IllegalArgumentException(TYPE_INVALID);
    }
}

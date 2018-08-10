package com.arxanfintech.common.structs;

public enum WalletType {
    GENERAL(1), // 1: 通用账户
    ORGANIZATION(2), // 2: 组织账户
    GENERALPERSON(3), // 3: 通用个人账户
    DEPENDENTPERSON(4), // 4: 独立个人账户(比如成人）
    INDEPENDENTPERSON(5);// 5：非独立个人账户(比如儿童）

    private int index;

    private WalletType(int _index) {
        this.index = _index;
    }

    public int getIndex() {
        return this.index;
    }
}

package com.arxanfintech.common.structs;

public enum WalletType {

    ASSET(0), ENTITY(1), // 1: 通用账户
    ORGANIZATION(2), // 2: 组织账户
    PERSON(3), // 3: 通用个人账户
    INDEPENDENT(4), // 4: 独立个人账户(比如成人）
    DEPENDENT(5), // 5：非独立个人账户(比如儿童）
    SERIALNUMBER(6), SWCASH(11), SWFEE(12), SWLOAN(13), SWINTEREST(14), DAPP(21), CHAINAPP(22);

    private int index;

    private WalletType(int _index) {
        this.index = _index;
    }

    public int getIndex() {
        return this.index;
    }
}

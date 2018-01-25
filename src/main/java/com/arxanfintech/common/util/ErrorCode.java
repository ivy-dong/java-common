/*******************************************************************************
Copyright ArxanFintech Technology Ltd. 2018 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

                 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*******************************************************************************/

package com.arxanfintech.common.util;

/**
 * 
 * ErrorCode ENUM
 *
 */
public enum ErrorCode {
    // common error code and message defined
    InvalidParamsErrCode(1000, ""), // 参数无效
    MissingParamsErrCode(1001, ""), // 缺少参数
    DatabaseOperationFailed(1002, ""), // 数据库操作失败
    ParseRequestParamsError(1003, ""), // 解析请求体失败
    SerializeDataFail(1004, ""), // 序列化数据失败
    DeserializeDataFail(1005, ""), // 反序列化(解析)数据失败
    GetServerContextFail(1006, ""), // 获取服务的上下文失败
    DatabaseUnavailable(1007, ""), // 数据库不可用
    DatabaseDisabled(1008, ""), // 数据库已禁用
    PermissionDenied(1009, ""), // 没有权限
    ED25519SignFail(1010, ""), // ED25519签名失败
    ED25519VerifyFail(1011, ""), // ED25519验签失败
    InternalServerFailure(1012, ""), // 服务内部错误

    // ccsandbox
    InvalidRequestBody(3000, ""), //
    UnavailableServerContext(3001, ""), //
    FailedChaincodeRetrieval(3002, ""), //
    FailedFabricCCInstallation(3003, ""), //
    FailedFabricCCInvocation(3004, ""), //
    FailedFabricCCQuery(3005, ""), //
    FailedFabricCCStop(3006, ""), //

    // fred
    RepeatRegistration(4000, ""), // 重复注册
    UnmarshalFailed(4003, ""), // Unmarshal失败
    DataTypeIsIncorrect(4004, ""), // 数据类型有误
    OriginalSecretError(4005, ""), // 原始Secret错误
    UsersDoNotHavePermission(4006, ""), // 用户无权限
    CertificateUnavailable(4007, ""), // 证书不可用
    NoSuchTypeOfUser(4008, ""), // 无此类型用户
    InvalidAccessOrSecret(4009, ""), // 无效的用户名或密码
    UserNotExist(4010, ""), // 无此用户

    // tomago
    RegisterEntityFail(5000, ""), // 注册实体失败
    UpdateEntityFail(5001, ""), // 更新实体失败
    QueryEntityFail(5002, ""), // 查询实体失败
    RegisterAssetFail(5003, ""), // 注册资产失败
    UpdateAssetFail(5004, ""), // 更新资产失败
    QueryAssetFail(5005, ""), // 查询资产失败
    QueryAuditInfoFail(5006, ""), // 查询审计信息失败
    AuditReverseFail(5007, ""), // 审计平账操作失败
    ChargeInterestFail(5008, ""), // 计息失败
    IssueCCoinFail(5009, ""), // 发行染色币失败
    RollbackTransactionFail(5010, ""), // 回滚交易失败
    TransferCCoinFail(5011, ""), // 转账交易失败
    TransferAssetFail(5012, ""), // 转移资产失败
    WithdrawFail(5013, ""), // 取现失败
    ColoredCoinNotFound(5014, ""), // 染色币没有找到
    BalancesNotSufficient(5015, ""), // 余额不足
    AssetHasNotOwner(5016, ""), // 资产没有所属人
    AssetHasBeenIssued(5017, ""), // 资产已发行
    AssetOwnerNotMatch(5018, ""), // 资产所属人不匹配
    WalletStatusInvalid(5019, ""), // 钱包状态异常
    EntityNotFound(5020, ""), // 组织实体找不到
    AssetNotFound(5021, ""), // 数字资产没有找到
    CCoinStatusNotInUse(5022, ""), // 染色币已回收
    CCoinAmountInvalid(5023, ""), // 染色币数额无效

    // wallet-webserver
    FailedToGenerateQRCode(6000, ""), // 生成二维码失败
    ImgFormatConversionFailed(6001, ""), // 图片转码失败

    // wallet-ng error code and message defined
    WalletNotFound(8000, ""), // 钱包对象没有找到
    WalletGetCCoinsFail(8001, ""), // 获取钱包染色币失败
    WalletGetAssetsFail(8002, ""), // 获取钱包资产失败
    WalletTransferCCoinsFail(8003, ""), // 钱包转账失败
    WalletTransferAssetsFail(8004, ""), // 钱包转移资产失败
    CreateMainWalletFail(8005, ""), // 创建主钱包失败
    CreateSubWalletFail(8006, ""), // 创建子钱包失败
    GetPublicKeyFail(8007, ""), // 获取公钥失败
    OffchainReadUploadFileFail(8008, ""), // 获取上传的文件失败
    OffchainSaveFileFail(8009, ""), // 保存文件失败
    OffchainDIDTypeInvalid(8010, ""), // 不正确的资产类型

    // chain-mgmt error code and message defined
    ChannelAlreadyCreated(9000, ""), // channel已经创建成功
    ChaincodeIDMissing(9001, ""), //
    ChaincodePathMissing(9002, ""), //
    CTypeUnrecognized(9003, ""), //
    MPartUnrecognized(9004, ""), //
    FileNotAccessible(9005, ""), //
    CCAlreadyCreated(9006, ""), // chaincode已经创建
    CCDeployRecordExisted(9007, ""), // chaincode deploy记录存在
    CCDeleteDeployed(9008, ""), // 删除已经部署的chaincode source
    ChaincodeNotExist(9009, ""), // chaincode记录不存在
    CCSandBoxFailed(9010, ""), // chaincode沙箱测试失败
    CCNotDeployed(9011, ""), // chaincode没有部署,""),不允许upgrade
    CCAlreadyDeployed(9012, ""), // chaincode已经部署过,""),不允许部署
    CCUpgradeNoResource(9013, "");// 无更新的chaincode资源去更新

    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error info: error code " + code + ", error message: " + message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

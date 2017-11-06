package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.device.IDCardReader;
import com.landicorp.android.eptapi.exception.RequestException;

import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * 该模块可用于已集成SAMV模块的终端。目前可适配W280PV2/W280PV3终端，其他终端暂不支持。
 */

public abstract class IDCardReaderImpl extends BaseDevice {

    private IDCardReader reader = IDCardReader.getInstance();

    public IDCardReaderImpl(Context context) {
        super(context);
    }

    public void searchCard() {
        try {
            reader.searchCard(new IDCardReader.OnSearchListener() {
                @Override
                public void onCardPass(byte[] bytes) {
                    displayInfo("card pass, data = " + ByteUtil.bytes2HexString(bytes));
                    try {
                        int ret = reader.selectCard();
                        if (ret != IDCardReader.ERROR_NONE) {
                            displayInfo("select card fail, error = " + ret);
                            return;
                        }
                        IDCardReader.IDCardInfo info = reader.readCardInfo();
                        if (info != null) {
                            int error = info.getErrorCode();
                            if (error == IDCardReader.ERROR_NONE) {
                                String name = info.getName();
                                String sex = info.getSex();
                                String birthDay = info.getBirthday();
                                String address = info.getAddress();
                                String expiredDate = info.getExpiredDate();
                                displayInfo("name = " + name);
                                displayInfo("sex = " + sex);
                                displayInfo("birthDay = " + birthDay);
                                displayInfo("address = " + address);
                                displayInfo("expiredDate = " + expiredDate);
                            } else {
                                displayInfo("search card fail, error = " + getDescription(error));
                            }
                        } else {
                            displayInfo("card info is null");
                        }
                    } catch (RequestException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(int error) {
                    displayInfo("search card fail, error = " + getDescription(error));
                }

                @Override
                public void onCrash() {
                    onDeviceServiceCrash();
                }

                private String getDescription(int error) {
                    switch (error) {
                        case ERROR_FAILED:
                            return "其他错误（如系统错误等）[" + error + "]";
                        case ERROR_TIMEOUT:
                            return "操作超时[" + error + "]";
                        case ERROR_TRANERR:
                            return "数据传输错误[" + error + "]";
                        default:
                            return "未知错误[" + error + "]";
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public void stopSearch() {
        try {
            reader.stopSearch();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
}

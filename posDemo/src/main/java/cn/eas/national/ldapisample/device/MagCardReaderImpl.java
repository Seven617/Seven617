package cn.eas.national.ldapisample.device;

import android.content.Context;
import android.text.TextUtils;

import com.landicorp.android.eptapi.device.MagCardReader;
import com.landicorp.android.eptapi.exception.RequestException;

/**
 * This sample show that how to read magnetic card
 *
 * @author chenwei
 */
public abstract class MagCardReaderImpl extends BaseDevice {
    MagCardReader.OnSearchListener listener = new MagCardReader.OnSearchListener() {
        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }

        @Override
        public void onFail(int code) {
            String errorMessage = getErrorDescription(code);
            displayInfo("search fail[error = " + code + ", des = " + errorMessage + "]");
        }

        @Override
        public void onCardStriped(boolean[] hasTrack, String[] track) {
            StringBuilder infoBuilder = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                infoBuilder.append("TRACK ");
                infoBuilder.append(i + 1);
                infoBuilder.append("：");
                infoBuilder.append(" [");
                infoBuilder.append(track[i]);
                infoBuilder.append("]");
                infoBuilder.append("\n");
            }
            boolean isBankCard = isBankCard(track[1]);
            if (isBankCard) {
                infoBuilder.append("cardNo = " + getCardNo(track[1]) + "\n");
                infoBuilder.append("expiredDate = " + getExpiredDate(track[1]) + "\n");
                infoBuilder.append("serviceCode = " + getServiceCode(track[1]) + "\n");
            }
            displayMagCardInfo(infoBuilder.toString());
        }

        /**
         * Get msg of the error code for display
         * @param code
         * @return
         */
        public String getErrorDescription(int code) {
            switch (code) {
                case ERROR_NODATA:
                    return "no data";
                case ERROR_NEEDSTART:
                    return "need restart search";//This error never happened
                case ERROR_INVALID:
                    return "has invalid track";//
            }
            return "unknown error - " + code;
        }
    };

    public MagCardReaderImpl(Context context) {
        super(context);
    }

    /**
     * Search card and show all track info
     */
    public void searchCard() {
        // start search card
        try {
            int enable = MagCardReader.TRK1 | MagCardReader.TRK2 | MagCardReader.TRK3;
            MagCardReader.getInstance().enableTrack(enable);
            MagCardReader.getInstance().setLRCCheckEnabled(true);
            MagCardReader.getInstance().searchCard(listener);

            showNormalMessage("Please stripe mag card!");
        } catch (RequestException e) {
            // the device service has a fatal exception
            e.printStackTrace();
            onDeviceServiceCrash();
        }
    }

    /**
     * Stop search if card searching is started
     */
    public void stopSearch() {
        try {
            MagCardReader.getInstance().stopSearch();
        } catch (RequestException e) {
            e.printStackTrace();
            onDeviceServiceCrash();
        }
    }

    public boolean isBankCard(String track2) {
        if (TextUtils.isEmpty(track2) || !track2.contains("=")) {// 不能为空 需有=号标志
            return false;
        }
        int separatorPosition = track2.indexOf("=");
        try {
            int year = Integer.parseInt(track2.substring(separatorPosition + 1, separatorPosition + 3));
            int month = Integer.parseInt(track2.substring(separatorPosition + 3, separatorPosition + 5));
            int serverCode = Integer.parseInt(track2.substring(separatorPosition + 5, separatorPosition + 8));
            for (int i = 0; i < separatorPosition; i++) { // 卡号为数字
                if (!Character.isDigit(track2.charAt(i))) {
                    return false;
                }
            }
            if (track2.length() <= 40 && track2.length() >= 21// 2磁道长度<=40 >=21
                    && separatorPosition >= 13 && separatorPosition <= 19 // 账号13到19之间
                    && (month > 0 || (month == 0 && year == 0)) && month < 13) { // 月份1到12,
                // 0000表示无失效日期
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private String getCardNo(String track2) {
        return track2.substring(0, track2.indexOf("="));
    }

    private String getServiceCode(String track2) {
        int sepIndex = track2.indexOf('=');
        int serviceCodeIndex = sepIndex + 5;
        if (serviceCodeIndex + 3 > track2.length()) {
            return null;
        }
        return track2.substring(serviceCodeIndex, serviceCodeIndex + 3);
    }

    private String getExpiredDate(String track2) {
        int expiredDateStart = track2.indexOf('=') + 1;
        String expiredDate = track2.substring(expiredDateStart, expiredDateStart + 4);
        return expiredDate;
    }

    /**
     * Display mag card info
     *
     * @param cardInfo
     */
    protected abstract void displayMagCardInfo(String cardInfo);
}

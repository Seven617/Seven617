package cn.eas.national.ldapisample.data;

/**
 * Created by Czl on 2017/8/11.
 */

public interface CameraScannerError extends BaseError {
    int INIT_DECODER_FAIL = 0x01;
    int HAS_CREATED = 0x02;
    int OPEN_CAMERA_FAIL = 0x03;
    int LICENSE_FAIL = 0x04;
    int NOT_FOUND_DECODRE = 0x05;
}

package cn.eas.national.ldapisample.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * 字节处理相关工具类
 * @author baoxl
 *
 */
public class ByteUtil {

	private static final String TAG = ByteUtil.class.getSimpleName();
	
	private ByteUtil() {}
	
	public static byte hex2byte(char hex) {
		if (hex <= 'f' && hex >= 'a') {
			return (byte) (hex - 'a' + 10);
		}

		if (hex <= 'F' && hex >= 'A') {
			return (byte) (hex - 'A' + 10);
		}

		if (hex <= '9' && hex >= '0') {
			return (byte) (hex - '0');
		}

		return 0;
	}
	
	public static int bytes2Int(byte[] data) {
		if (data == null || data.length == 0) {
			return 0;
		}

		int total = 0;
		for (int i = 0; i < data.length; i++) {
			total += (data[i] & 0xff) << (data.length - i - 1) * 8;
		}
		return total;
	}
	
	public static long bytes2Long(byte[] data) {
		if (data == null || data.length == 0) {
			return 0;
		}

		long total = 0;
		for (int i = 0; i < data.length; i++) {
			total += (data[i] & 0xff) << (data.length - i - 1) * 8;
		}
		return total;
	}
	
	public static String bytes2HexString(byte[] data) {
		if (data == null)
			return "";
		StringBuilder buffer = new StringBuilder();
		for (byte b : data) {
			String hex = Integer.toHexString(b & 0xff);
			if (hex.length() == 1) {
				buffer.append('0');
			}
			buffer.append(hex);
		}
		return buffer.toString().toUpperCase(Locale.US);
	}
	
	public static byte[] hexString2Bytes(String data) {
		if (data == null)
			return null;
		byte[] result = new byte[(data.length()+1)/2];
		if ((data.length() & 1) == 1) {
			data += "0";
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (hex2byte(data.charAt(i*2+1)) | (hex2byte(data.charAt(i*2))<<4));
		}
        return result;
    }
	
	public static String bcd2Ascii(final byte[] bcd) {
		if (bcd == null)
			return "";
    	StringBuilder sb = new StringBuilder(bcd.length << 1);
    	for (byte ch : bcd) {
    		byte half = (byte) (ch >> 4);
    		sb.append((char)(half + ((half > 9) ? ('A' - 10) : '0')));
    		half = (byte) (ch & 0x0f);
    		sb.append((char)(half + ((half > 9) ? ('A' - 10) : '0')));
    	}
    	return sb.toString();
    }
	
	public static byte[] ascii2Bcd(String ascii) {
		if (ascii == null)
			return null;
		if ((ascii.length() & 0x01) == 1)
			ascii = "0" + ascii;
		byte[] asc = ascii.getBytes();
		byte[] bcd = new byte[ascii.length() >> 1];
		for (int i = 0; i < bcd.length; i++) {
			bcd[i] = (byte)(hex2byte((char)asc[2 * i]) << 4 | hex2byte((char)asc[2 * i + 1]));
		}
		return bcd;
	}
	
	public static byte[] toBytes(String data, String charsetName) {
		try {
			return data.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static byte[] toBytes(String data) {
		return toBytes(data, "ISO-8859-1");
	}
	
	public static byte[] toGBK(String data) {
		return toBytes(data, "GBK");
	}
	
	public static byte[] toGB2312(String data) {
		return toBytes(data, "GB2312");
	}
	
	public static byte[] toUtf8(String data) {
		return toBytes(data, "UTF-8");
	}
	
	public static String fromBytes(byte[] data, String charsetName) {
		try {
			return new String(data, charsetName);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String fromBytes(byte[] data) {
		return fromBytes(data, "ISO-8859-1");
	}
	
	public static String fromGBK(byte[] data) {
		return fromBytes(data, "GBK");
	}
	
	public static String fromGB2312(byte[] data) {
		return fromBytes(data, "GB2312");
	}
	
	public static String fromGB2312New(String data) {
		return fromGB2312(toBytes(data.trim()));
	}
	
	public static String fromUtf8(byte[] data) {
		return fromBytes(data, "UTF-8");
	}
	
	public static void dumpHex(String msg, byte[] bytes) {
        int length = bytes.length;
        msg = (msg == null) ? "" : msg;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n---------------------- " + msg + "(len:%d) ----------------------\n", length));
        for (int i = 0; i < bytes.length; i++) {
            if (i % 16 == 0) {
                if (i != 0) {
                	sb.append('\n');
                }
                sb.append(String.format("0x%08X    ", i));
            }
            sb.append(String.format("%02X ", bytes[i]));
        }
        sb.append("\n----------------------------------------------------------------------\n");
	}

	public static String str2HexStr(String str) {
		final char[] mChars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes();

		for (int i = 0; i < bs.length; i++) {
			sb.append(mChars[(bs[i] & 0xFF) >> 4]);
			sb.append(mChars[bs[i] & 0x0F]);
		}
		return sb.toString().trim();
	}

	public static byte[] merge(byte[]... data) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			if (data != null && data.length > 0) {
				for (byte[] bytes : data) {
					if (bytes != null && bytes.length > 0) {
						stream.write(bytes);
					}
				}
				stream.flush();
				return stream.toByteArray();
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


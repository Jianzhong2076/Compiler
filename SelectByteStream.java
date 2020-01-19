package com.zccp.tongyin;

public class SelectByteStream {
	/**
	 * @Fuction isBlank
	 * @return boolean value
	 */
	public static boolean isBlank(byte a) {
		if (a == 0x20 || a == 0x0d || a == 0x0a || a == 0x09) { // space, return, NL, tab
			return true;
		}else {
			return false;
		}
	}

	public static boolean isSpace(byte c) {
	    return (c == 0x20);
    }

    public static boolean isTab(byte c) {
	    return (c == 0x09);
    }

    public static boolean isLine(byte c1, byte c2) {
	    return ((c1 == 0x0d) && (c2 == 0x0a));
    }
	/**
	 * 
	 * @Funtion ignoreBlank
	 * @param pos, bytes
	 * @return i as current position
	 */
	public static int ignoreBlank(int pos, byte[] bytes) {
//		int i;
//		for (i = pos; i < bytes.length; i++) {
//			if (isBlank(bytes[i])) {
//				continue;
//			}else {
//				return i;
//			}
//		}
//		return i;

        int i;
		for (i = pos; i < bytes.length; i++) {
		    if (isSpace(bytes[i])) {
                ProcessByteStream.column++;
            } else if (isTab(bytes[i])) {
                ProcessByteStream.column += 4;
            } else if (isLine(bytes[i], bytes[i + 1])) {
                ProcessByteStream.column = 0;
                ProcessByteStream.row++;
                i += 1;
            } else {
		        return i;
            }
		}
		return i;
	}

    /**
	 * @Function strPrint
	 * @return void
	 */
	public static void strPrint(String st) {
		if ((st).equals(";")) {
			System.out.println(st);
		}else {
			System.out.print(" " + st);
		}
	}
	
		
}

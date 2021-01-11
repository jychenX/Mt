package tools;

public class StringUtils {

//	public static String fileAsString(String fileName){
//		BufferedReader br = null;
//		ByteArrayOutputStream bos = null;
//		try {
//
//			br = new BufferedReader(new FileReader(fileName));
//			bos = new ByteArrayOutputStream();
//
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				String line2 = StringEscapeUtils.unescapeJava(line);
//				bos.write(line2.getBytes());
//				bos.write("\r\n".getBytes());
//			}
//
//			String result = new String(bos.toByteArray());
//			System.out.println("result:" + result);
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(br != null){
//				try {
//					br.close();
//				} catch (Exception e2) {
//					// TODO: handle exception
//				}
//			}
//		}
//		return null;
//	}

	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
						.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}

}

package com.zccp.tongyin;

import javafx.geometry.Pos;

import java.util.*;

import java.lang.*;

public class ProcessByteStream {

	private static int current = 0; // 元空间包括：字段、常量池和方法区，静态字段属于全局变量，所有成员变量共享；
    public static int row = 0; // 行号 => 变化规则...
    public static int column = 0; // 列号 => 变化规则...

    static String  src;
	
	private static Map dictionary = new HashMap();

        public static void main(String[] args) throws Exception {
		src = MyFile.read();
		while (true) {
			Token token = fetchToken();
            Error error = match(token);
            if (error.getCode() == 0) {
                continue;
            } else {
                System.out.println(error.toString());
                return;
            }
		}

	}

	/**
	 * @category fetchToken
	 * 
	 */

	public static Token fetchToken() {
		int count = 0;
		int start = -1;
		int lineNumber;
		byte[] value = null;
		byte[] bytes = src.getBytes();

		for (int end = SelectByteStream.ignoreBlank(current, bytes); end < bytes.length; end++) {
			if (start == -1) {
				start = end;
			}
			count++;
			if (end - start + 1 == count && SelectByteStream.isBlank(bytes[end + 1])) {
				value = new byte[count]; // 按照读到有效字符的位数创建存放值的新数组
				//lineNumber = count;
				
				int m = 0;
				for (int n = start; n <= end; n++, m++) {
					value[m] = bytes[n];
				}
				if (m == count) {
					current = end + 1;
					String  st = new String(value); // 构造函数不能被直接调用，必须通过new运算符在创建对象时自动调用，
					// SelectByteStream.strPrint(st);
					Token token = new Token(st, row, column,"JavaTest.txt");
                    column += count;
					return token;
				}
			}
		}
		return null;
	}

	/**
	 * @category match
	 * @param token
	 * @return
	 */
//基于已经编写的分词器，对所输入的字符流进行词法识别，看每个输入的单词对不对？
//关键字：String和print
//变量名：128个字符，连续，a~z，A~Z，非法变量名
// 运算符：单目：~（按位取反）、! (取非)、-（负号运算符）、 ++（自增）、 - -（自减）、双目：+ - * / %（取余）；
//关系：等于符号:==，不等于符号:!= ，大于符号:>， 小于符号:<，大于等于符号:>= ，小于等于符号:<= 。
// 常量：字符串“0~9”；
//分号：以分号结尾；

	public static Error match(Token token) { // token is a String
		String id0 = null;
		String id1 = null;

		switch (token.value) {
		case "string":
			Token id = fetchToken(); // fetchToken处理结果是连续的字符串
			Token temp0, temp1;
			Error error = isIdentifier(id); // 1.是否是标识符，不是返回false（第一次取值）
			if (error.getCode() != 0) {
                return error;
			}

			temp0 = fetchToken();
			if (temp0.value.equals("=")) { // 2. 是标识符，且下一个元素等于" = "（第二次取值）
				temp0 = fetchToken();
                error = isIdentifierOrLiteral(temp0);
				if (error.getCode() == 0) { 	  // 3. " = "后的下一个元素为字面量（第三次取值）
                    temp1 = fetchToken();
					if (temp1.value.equals(";")) { // 4. 判断字面量或者标识符后面是" ; "的话（第四次取值）
						dictionary.put(id.value, temp0.value.substring(1, temp0.value.length() - 1)); 
														  // 5. 将标识符（第一次取值）和字面量或者标识符（第三次取值）存入字典
						return new Error(0, null, null, null);
					}
					return new Error(7, "no \";\"!" ,"should be \";\"!", new Position(temp1.row, temp1.column, "xxxxxx"));
				} else if (error.getCode() == 100) { // 6. 假如是标识符（第三次取值）， c = a + b
					id0 = (String) dictionary.get(temp0.value); // 7. 将该标识符（第三次取值）的值赋给中间变量id1
                    temp1 = fetchToken();
					if (temp1.value.equals("+")) { // 8. 假如该标识符(第四次取值)为" + "
						temp0 = fetchToken(); // 9. 将第五次取值赋值给temp0？
                        error = isIdentifier(temp0);
						if (error.getCode() == 0) { // 10. 判断第五次取值是否是标识符

							id1 = (String) dictionary.get(temp0.value); // 11.第五次取值是标识符，将值赋值给id2
                            temp1 = fetchToken();
							if (temp1.value.equals(";")) {
//								row = (fetchToken().value).indexOf();
//								column = temp0.column;
//								sourceFile = temp0.sourceFile;
								int id2 = Integer.parseInt(id0, 10) + Integer.parseInt(id1, 10);
								String id3 = new Integer(id2).toString();
								dictionary.put(id.value, id3);
                                return new Error(0, "success!", "none!", new Position(temp0.row, temp0.column, "xxxxxx"));
							} else {
                                return new Error(7, "no \";\"!" ,"should be \";\"!", new Position(temp1.row, temp1.column, "xxxxxx"));
                            }
						}
                        return error;
					} else {
                        return new Error(8, "no \"+\"!" ,"should be \"+\"!", new Position(temp1.row, temp1.column, "xxxxxx"));
                    }
				} else
                    return error;

			} else {
                return new Error(9, "no \"=\"!" ,"should be \"=\"!", new Position(temp0.row, temp0.column, "xxxxxx"));
            }

		case "print":
			id = fetchToken();
			error = isIdentifier(id);
			if (error.getCode() != 0)
                return error;
			temp0 = fetchToken();
			if (!temp0.value.equals(";"))
                return new Error(7, "no \";\"!" ,"should be \";\"!", new Position(temp0.row, temp0.column, "xxxxxx"));
			System.out.println(id.value + " = " + "\"" + (String) dictionary.get(id.value) + "\"" + ";");

		default:
            return new Error(1, "no keyword!", "none!", new Position(token.row, token.column, "xxxxx"));
		}
	}

    public static Error isIdentifierOrLiteral(Token token) {
        Error error = isLiteral(token);
        if (error.getCode() != 0) {
            error = isIdentifier(token);
            if (error.getCode() != 0) {
                return new Error(6, "none!", "none!", new Position(token.row, token.column, "xxxxxx"));
            } else {
                return new Error(100, "success!", "none!", new Position(token.row, token.column, "xxxxxx"));
            }
        }
        return new Error(0, "success!", "none!", new Position(token.row, token.column, "xxxxxx"));
    }

    public static Error isIdentifier(Token id) { // 标识符函数
        if (id.value.length() > 128) {
            return new Error(2, "the lenght of the identifier is more than 128!",
                    "should be less than and equal 128!", new Position(row, column, "xxxxxx"));
        }

        int i;
        for (i = 0; i < id.value.length(); i++) {
            char chr = id.value.charAt(i); // charAt() 方法用于返回指定索引处的字符，索引范围为从 0 到 length() - 1
            if (chr <= 'z' && chr >= 'a' || chr <= 'Z' && chr >= 'A') {

            } else {
                return new Error(3, "the letter of the identifier is out of alphabet!", "should be a-z or A-Z",
                        new Position(id.row, id.column + i, "xxxxxx"));
            }
        }
        return new Error(0, "success!", "none!", new Position(id.row, id.column, "xxxxxx"));
    }


	public static boolean isNumber(char c) { // 实数函数
//		if (c <= '9' && c >= '0') {
//			return true;
//		}
//		return false;
		return (c <= '9' && c >= '0');
	}

	public static Error isLiteral(Token it) { // 字面量函数
        String value = it.value;
		int len = value.length();
		if (value.charAt(0) != '\"') {
            return new Error(4, "the first char is not \"", "should be \"", new Position(it.row, it.column, "xxxxxx"));
        }

		if (value.charAt(len - 1) != '\"') {
            return new Error(5, "the last char is not \"", "should be \"", new Position(it.row, it.column + len - 1, "xxxxxx"));
        }

        for (int i = 1; i < len - 2; i++) {
            if (!isNumber(value.charAt(i))) {
                return new Error(6, "the literal is not number!", "should be 0-9!", new Position(it.row, it.column + i, "xxxxxx"));
            }
        }

        return new Error(0, "success!", "none!", new Position(it.row, it.column, "xxxxxx"));
	}

}

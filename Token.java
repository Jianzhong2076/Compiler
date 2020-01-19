package com.zccp.tongyin;

public class Token extends Object {	//	构造函数必须和类名完全相同，主要用于在类的对象创建时定义初始化的状态，

    String value;
	int row;
	int column;
    String sourceFile;

    public Token(String value, int row, int column, String sourceFile) {
        this.value = value;
        this.row = row;
        this.column = column;
        this.sourceFile = sourceFile;
    }
}



package com.zccp.tongyin;

public class Position {
    private int row;
    private int column;
    private String sourceFile;

    public Position(int row, int column, String sourceFile) {
        this.row = row;
        this.column = column;
        this.sourceFile = sourceFile;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                ", sourceFile='" + sourceFile + '\'' +
                '}';
    }
}
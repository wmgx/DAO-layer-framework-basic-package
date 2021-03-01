package top.wmgx;


import top.wmgx.model.Test;
import top.wmgx.utils.MapperUtils;

public class Main {
    public static void main(String[] args) {

        System.out.println(MapperUtils.query(Test.class, "select * from roles"));

    }
}
	
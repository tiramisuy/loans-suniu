package com.rongdu.loans.credit.common;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * 中文分词工具类
 * @author sunda
 * @version 2017-08-14
 */
public class IkanalyzerUtils {

    //阈值
    public static double DEFAULT_YUZHI = 0.2 ;

    /**
     * 中文分词
     * @param str
     * @return
     */
    public static Vector<String> participle(String str ) {
        //对输入进行分词
        Vector<String> list = new Vector<String>() ;
        try {
            StringReader reader = new StringReader( str );
            //当为true时，分词器进行最大词长切分
            IKSegmenter ik = new IKSegmenter(reader,true);
            Lexeme lexeme = null ;
            while( ( lexeme = ik.next() ) != null ) {
                list.add( lexeme.getLexemeText() );
            }
            if( list.size() == 0 ) {
                return null ;
            }
            //分词后
//	        System.out.println( "str分词后：" + list );
        } catch ( IOException e1 ) {
            System.out.println();
        }
        return list;
    }

    /**
     * 计算分词之间的相似度
     * @param T1
     * @param T2
     * @return
     * @throws Exception
     */
    public static double getSimilarity(Vector<String> T1, Vector<String> T2) throws Exception {
        int size = 0 , size2 = 0 ;
        if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {

            Map<String, double[]> T = new HashMap<String, double[]>();

            //T1和T2的并集T
            String index = null ;
            for ( int i = 0 ; i < size ; i++ ) {
                index = T1.get(i) ;
                if( index != null){
                    double[] c = T.get(index);
                    c = new double[2];
                    c[0] = 1; //T1的语义分数Ci
                    c[1] = DEFAULT_YUZHI;//T2的语义分数Ci
                    T.put( index, c );
                }
            }

            for ( int i = 0; i < size2 ; i++ ) {
                index = T2.get(i) ;
                if( index != null ){
                    double[] c = T.get( index );
                    if( c != null && c.length == 2 ){
                        c[1] = 1; //T2中也存在，T2的语义分数=1
                    }else {
                        c = new double[2];
                        c[0] = DEFAULT_YUZHI; //T1的语义分数Ci
                        c[1] = 1; //T2的语义分数Ci
                        T.put( index , c );
                    }
                }
            }

            //开始计算，百分比
            Iterator<String> it = T.keySet().iterator();
            double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
            while( it.hasNext() ){
                double[] c = T.get( it.next() );
                Ssum += c[0]*c[1];
                s1 += c[0]*c[0];
                s2 += c[1]*c[1];
            }
            //百分比
            double similarity = Ssum / Math.sqrt( s1*s2 );
            return similarity;
        } else {
            throw new Exception("传入参数有问题！");
        }
    }

    /**
     * 计算两个字符串之前的相似度
     * @param str1
     * @param str2
     * @return
     */
    public static double getSimilarity(String str1, String str2) {
        try {
            return getSimilarity(participle(str1),participle(str2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0D;
    }
}

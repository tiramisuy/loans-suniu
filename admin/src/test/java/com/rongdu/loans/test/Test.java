package com.rongdu.loans.test;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.CostUtils;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.jdq.JdqUtil;
import com.rongdu.loans.common.ThirdApiDTO;
import com.rongdu.loans.common.dwd.DwdUtil;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.loan.option.dwd.report.CheckPoints;
import com.rongdu.loans.loan.vo.LoanApplyVO;
import com.rongdu.loans.risk.vo.HitRuleVO;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Test {

    static List<String> list = new ArrayList<>();
    static {
        for (int i = 0; i < 100; i++) {
            list.add("票编号"+i);
        }
    }
    public static void main(String[] args) throws OSSException, ClientException, FileNotFoundException {
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("Uid", "34460");
		Result result = (Result) RestTemplateUtils.getInstance().postForObject("http://longjun.toufuli.com/Borrower/GetSTSToken", map, Result.class);
		Map<String, String> resultMap = (Map<String, String>) result.getData();
		String accessKeyId = resultMap.get("AccessKeyId");
		String accessKeySecret = resultMap.get("AccessKeySecret");
		String securityToken = resultMap.get("SecurityToken");
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";
		OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret, securityToken);
		String bucketName = "tflborrowimage";
		String key = "noimage.png"; //文件名
		String filename = "C://noimage.png";
		uploadFile(client, bucketName, key, filename);
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		// 生成URL
		URL url = client.generatePresignedUrl(bucketName, key,expiration);
		System.out.println(url);*/
        /*ExecutorService executorService = Executors.newFixedThreadPool(5);
        long starTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        Queue<String> queue = new ConcurrentLinkedDeque<>(list);
        final AtomicInteger num = new AtomicInteger();
        final AtomicInteger total = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            //while (queue.size()>0){
            //System.out.println(queue.size());
            executorService.execute(() -> {
                //String poll = queue.poll();
                String poll = list.remove(0);
                if (poll != null) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + poll);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                            *//*if (poll.equals("票编号88")){
                                System.out.println(Thread.currentThread().getName()+":"+poll+"error");
                                int a = 1/0;
                            }*//*
                        num.getAndAdd(1);
                    } finally {
                        countDownLatch.countDown();
                        total.getAndAdd(1);
                        //System.out.println(total.intValue());
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "null");
                }
            });
        }
        //}
        *//*for (String s : list) {
            System.out.println(Thread.currentThread().getName()+":"+s);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*//*
        try {
            countDownLatch.await();
            System.out.println(123123123);
            System.out.println(num.intValue());
            System.out.println(total);
            long endTime = System.currentTimeMillis();
            System.out.println(endTime-starTime);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*System.out.println(System.currentTimeMillis() - 1000 * 60 * 2);
        Date parse = DateUtils.parse("2019-06-03 11:54:17", PayChannelEnum.BAOFOO.getTimePattern());
        String s = DateUtils.formatDate(parse, "yyyy-MM-dd");
        System.out.println(s);
        int i = DateUtils.daysBetween(parse, DateUtils.parse("2019-04-01","yyyy-MM-dd"));
        System.out.println(i);*/
        String s = DateUtils.formatDateTime(1560096000000l);
        System.out.println(s);
        String url =
                UriComponentsBuilder.newInstance().scheme("http").host
                        ("47.104.17.142:9090").path("/#").path("/dwdAgreementList").build().toString();
        System.out.println(url);
        System.out.println(System.currentTimeMillis() - 1000 * 60 * 2);
        /*List<String> list = Arrays.asList("java", "scala", "python", "shell", "ruby");
        List<String> num = list.parallelStream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length()<5;
            }
        }).collect(Collectors.toList());
        //list.stream().map()
        System.out.println(num);
        System.out.println(list);*/
        String str1 = new String("j") + new String("java"); // 1
        str1.intern(); // 2
        String str2 = "jjava"; // 3
        System.out.println(str1 == str2); // 4

        String str3 = new String("ja") + new String("va2"); // 1
        String str4 = "java2"; // 2
        str3.intern(); // 3
        System.out.println(str3 == str4);


        /*Date date = DateUtils.parseDate(new Date().toString());
        System.out.println(new Date().toString());
        String repayDate = DateUtils.formatDate(date, "yyyy-MM-dd");
        System.out.println(repayDate);*/

        DecimalFormat decimalFormat = new DecimalFormat("##.00%");
        System.out.println(decimalFormat.format(1 / 3.0));

        System.out.println("=========================================");
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        System.out.println("百分数：" + nt.format(2 / 3));
        System.out.println("百分数：" + nt.format(1.0 / 2));

        List<HitRuleVO> list = new ArrayList<>();
        HitRuleVO vo = new HitRuleVO();
        vo.setRuleId("123");
        HitRuleVO vo1 = new HitRuleVO();
        //vo.setRuleId("123");
        //list.add(vo1);
        list.add(vo);
        list.stream().filter((Predicate<HitRuleVO>) (x ->
            x.getRuleId().equals("")));
        Map<Boolean, List<HitRuleVO>> collect =
                list.stream().collect(Collectors.partitioningBy(rule -> "123".equals(rule.getRuleId())));
        System.out.println(collect);
        System.out.println(list);
        System.out.println(collect.get(true));
        System.out.println(collect.get(false).isEmpty());
        System.out.println(222l/222l == 1);
        //System.out.println(123123);


        int a = Integer.valueOf("467");
        System.out.println(a<500);
    }
    private final static Lock lock = new ReentrantLock();

    private static void uploadFile(OSSClient client, String bucketName,
                                   String key, String filename) throws OSSException, ClientException,
            FileNotFoundException {
        File file = new File(filename);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        objectMeta.setContentType("image/png");

        InputStream input = new FileInputStream(file);
        PutObjectResult rs = client.putObject(bucketName, key, file, objectMeta);
        client.shutdown();
        System.out.println("上传成功：" + rs.getETag());
    }
}

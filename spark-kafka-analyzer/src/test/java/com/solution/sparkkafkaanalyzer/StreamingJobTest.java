package com.solution.sparkkafkaanalyzer;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class StreamingJobTest {

//    @Autowired
//    private KafkaSparkMeetUpConsumer consumer;
//
////    private static KafkaConsumer<String, String> consumer;
//
//    @BeforeClass
//    public static void setUpClass()  {
////        Properties properties = new Properties();
////        properties.put("bootstrap.servers", "localhost:9090");
////        properties.put("subscribe", "topic1");
////        properties.put("startingOffsets", "earliest");
////        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////
////        consumer = new KafkaConsumer<>(properties);
//    }
//
//
//    @Test
//    public void create_test() {
//        ConsumerRecords<String, String> records;
//        Thread thread = new Thread(() -> {
//            consumer.getMeetUpData();
//        });
//        thread.start();
//
//        //send a message to MQ.
//
//        MqSender mqSender = new MqSender();
//        mqSender.mqPushMsg("TestMsg");
//
//        //keep polling the kafka topic.
//
//        while(true){
//            System.out.println("Polling...");
//            records = consumer.poll(100);
//
//            if(!records.isEmpty()){
//
//                thread.interrupt();
//                break;
//            }
//
//            assertNotNull(records);
//
//        }
//    }
}

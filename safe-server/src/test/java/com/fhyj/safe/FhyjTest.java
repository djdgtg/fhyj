package com.fhyj.safe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhyj.safe.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/15 9:49
 */
@SpringBootTest
@Slf4j
public class FhyjTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test1() {
        log.info("After encryption: {}", stringEncryptor.encrypt("fhyj_elastic"));
    }

    @Test
    public void test2() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("10.9.1.130", 5044));
        //这里将你的实体类转成json
        String json = objectMapper.writeValueAsString(new Device());
        //特别注意 ，json换行，换行代表数据发送完毕，要不然logstash会一直接收数据，从而导致数据格式错误无法将数据写到es
        json += "\r\n";
        ByteBuffer buf = ByteBuffer.allocate(json.getBytes().length);
        buf.put(json.getBytes());
        buf.flip();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        buf.clear();
    }

    public static void main(String[] args) {
        int total = 43, BATCH_SIZE = 10;
        for (int i = 1; i <= total / BATCH_SIZE + 1; i++) {
            if (i == total / BATCH_SIZE + 1) {
                log.info("查询第{}页，有数据{}条", i, total % BATCH_SIZE);
            } else {
                log.info("查询第{}页，有数据{}条", i, BATCH_SIZE);
            }
        }
    }

}

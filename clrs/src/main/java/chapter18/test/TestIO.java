package chapter18.test;

import chapter18.io.FileBlockStore;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Test chapter18.io
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/29 16:09
 */
public class TestIO {

    public static final String FILE_PATH = "/tmp/test.data";
    public static final int BLOCK_SIZE = 512; //kb

    @Test
    public void testFileBlockStore() {
        FileBlockStore storage = new FileBlockStore(FILE_PATH, BLOCK_SIZE, false);
        storage.delete();
        storage.open();

        final FileBlockStore.WriteBuffer wbuf = storage.set(0);
        final ByteBuffer buf = wbuf.buf();
        buf.putInt(314);
        buf.putLong(1024L);
        buf.flip();
        wbuf.save();

        storage.sync();

        ByteBuffer byteBuffer = storage.get(0);

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());

        storage.close();
    }
}

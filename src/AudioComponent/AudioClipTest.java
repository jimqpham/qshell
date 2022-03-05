//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AudioClipTest {
//
//    @Test
//    public void testSetSample() {
//
//        AudioClip clip = new AudioClip();
//        clip.setSample(0, (short) -32768);  // 0x8000
//        clip.setSample(1, (short) -19773);  // 0xB2C3
//        clip.setSample(2, (short) 0);       // 0x0000
//        clip.setSample(3, (short) 15967);   // 0x3E5F
//        clip.setSample(4, (short) 32767);   // 0x7FFF
//
//        Assertions.assertEquals("00 80 C3 B2 00 00 5F 3E FF 7F ", clip.toHexString(10));
//
//    }
//
//    @Test
//    public void testGetSample() {
//
//        AudioClip clip = new AudioClip();
//        clip.setSample(0, (short) -32768);  // 0x8000
//        clip.setSample(1, (short) -19773);  // 0xB2C3
//        clip.setSample(2, (short) 0);       // 0x0000
//        clip.setSample(3, (short) 15967);   // 0x3E5F
//        clip.setSample(4, (short) 32767);   // 0x7FFF
//
//        Assertions.assertEquals(-32768, clip.getSample(0));
//        Assertions.assertEquals(-19773, clip.getSample(1));
//        Assertions.assertEquals(0, clip.getSample(2));
//        Assertions.assertEquals(15967, clip.getSample(3));
//        Assertions.assertEquals(32767, clip.getSample(4));
//
//    }
//
//}
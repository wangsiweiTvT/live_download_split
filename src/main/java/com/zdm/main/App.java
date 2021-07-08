package com.zdm.main;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

/**
 * Hello world!
 *
 */
public class App {


    public static void frameRecord(String inputFile) throws Exception{

        //rtsp视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        grabber.setOption("rtsp_transport","tcp");
        grabber.setFrameRate(30);
        grabber.setVideoBitrate(3000000);
        //输出地址/分辨率/是否录制音频
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("./target/mv.mp4", 1280, 720,1);
        recorder.setFrameRate(30);
        recorder.setVideoBitrate(3000000);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);//打不开待解决
        try {
            grabber.start();
            recorder.start();//录制器
            Frame frame = grabber.grabFrame();
            while (frame!= null) {
                //按帧录制,可以加水印再保存
                recorder.record(frame);
            }
            grabber.stop();
            recorder.stop();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (grabber != null) {
                grabber.stop();
            }
        }
    }

    public static void main(String[] args) throws Exception{
//        String inputFile = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";//inputFile可以是本地视频文件、rtsp地址、rtmp地址
//        frameRecord(inputFile);





    }


}

package com.zdm.main;

import org.bytedeco.ffmpeg.global.avcodec;


import org.bytedeco.javacv.*;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;

/**
 *
 * 调用本地摄像头窗口视频
 * @Auther: wangsiwei
 * @Date: 2021/7/2 18:42
 * @Description:
 */
public class NativeCamera2OBS {

    public static void main(String[] args) throws Exception, InterruptedException{

        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);//新建opencv抓取器，一般的电脑和移动端设备中摄像头默认序号是0，不排除其他情况
        grabber.start();//开始获取摄像头数据

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        OpenCVFrameConverter.ToOrgOpenCvCoreMat toOrgOpenCvCoreMat = new OpenCVFrameConverter.ToOrgOpenCvCoreMat();
        CanvasFrame canvas = new CanvasFrame("摄像头预览");//新建一个预览窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //1280
        int imageWidth = grabber.getImageWidth();
        //720
        int imageHeight = grabber.getImageHeight();


        double frameRate = grabber.getFrameRate();
        int audioChannels = grabber.getAudioChannels();
        //0
        int videoCodec = grabber.getVideoCodec();
        //0
        int audioCodec = grabber.getAudioCodec();
        String format = grabber.getFormat();
        System.out.println(frameRate);
        System.out.println(format);
        System.out.println(videoCodec);
        System.out.println(audioCodec);


        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("/Users/wangsiwei/Desktop/record.mp4",imageWidth,imageHeight,audioChannels);

        recorder.setGopSize(2*(int)frameRate);
        recorder.setFrameRate(frameRate);
        recorder.setFormat("flv");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();
        Frame grab = grabber.grab();

        Point point = new Point(1200, 500);
        Scalar scalar = new Scalar(0, 255, 255, 0);
        //窗口是否关闭
        while(grab!=null){

            //
            Mat mat = toOrgOpenCvCoreMat.convertToOrgOpenCvCoreMat(grab);
            Imgproc.putText(mat,"SMZDM",point,Imgproc.FONT_HERSHEY_PLAIN,2.2,scalar,1,20,false);
            Frame frame = converter.convert(mat);
            /*获取摄像头图像并在窗口中显示,这里Frame frame=grabber.grab()得到是解码后的视频图像*/
            canvas.showImage(frame);
            grab = grabber.grab();
            recorder.record(frame);
            mat.release();
        }
        grabber.close();//停止抓取
    }








}



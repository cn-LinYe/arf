//package org.arf.core;
//
//import com.sun.jna.Library;
//import com.sun.jna.Native;
//import com.sun.jna.Callback;
//
//public class JnaCallback {
//	// 定义接口CLibrary，继承自com.sun.jna.Library
//	public interface CLibrary extends Library {
//		// 定义接口JavaCallbackAdd，继承自com.sun.jna.Callback
//		interface JavaCallbackAdd extends Callback {
//			int CallbackAdd(int a, int b);
//		}
//
//		// 动态库的函数声明
//		void RegisterAdd(JavaCallbackAdd callback);
//
//		void DoAddByCallback(int a, int b);
//	}
//
//	public static void main(String[] args)
//    {
//     //接口实例化并初始化实例
//     CLibrary lib = (CLibrary)Native.loadLibrary("JnaCbApi",CLibrary.class);
//     CLibrary.JavaCallbackAdd callback=new CLibrary.JavaCallbackAdd()
//  {
//              //实现CallbackAdd函数
//   public int CallbackAdd(int a,int b)
//   {
//    return a+b;
//   }
//    }
//    }
//}
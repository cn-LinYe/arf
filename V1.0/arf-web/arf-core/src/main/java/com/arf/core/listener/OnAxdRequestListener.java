package com.arf.core.listener;import com.arf.core.dto.AxdGiftResultDto;/** * 点击安心点请求事件监听器 * @author Caolibao * 2016年8月25日 下午4:02:07 * */public interface OnAxdRequestListener{		/**	 * 缓存监听器的key,数据结构为一个redis-list	 */	String Key_Cached_Listeners = "OnAxdRequestListener.List";		/**	 * 接收到安心点请求时即回调该方法	 * @param userName	 * @param license	 */	void onAxdRequest(String userName, String license);	/**	 * 安心点请求成功时即回调该方法	 * @param userName	 * @param license	 */	AxdGiftResultDto onAxdSuccess(String userName, String license);	/**	 * 安心点请求失败时即回调该接口	 * @param userName	 * @param license	 */	void onAxdFailed(String userName, String license);}
package com.arf.core.file;

public enum FileType{
	IMAGE,
	DOC,
	VIDEO,
	SOUND,
	EXE,
	OTHERS,
	ADS,//广告文件(图片,viedo等都存放此处)
	PDF,
	;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	};
}
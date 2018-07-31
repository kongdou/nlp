#encoding=utf-8
#斯坦福NLP
import sys
import os

class StandfordCoreNLP():
	def __init__(self,jarpath):
		self.root = jarpath
		self.tempsrcpath = "tempsrc" # 输出临时文件路径
		self.jarlist = ["ejml-0.23.jar","javax.json.jar","jollyday.jar","joda-time.jar","protobuf.jar","slf4j-api.jar","slf4j-simple.jar","standford-corenlp-3.6.0.jar","xom.jar"]
		self.jarpath = ""
		self.buildjars()
	
	def buildjars(self): # 根据root路构建所有的jar包路径
		for jar in self.jarlist:
			self.jarpath += self.root+jar+";"
	
	def savefile(self,path,sent): #创建临时文件路径
		fp = open(path,"wb")
		fp.write(sent)
		fp.close()
	
	def delfile(self,path):
		os.remove(path)

class StandfordPOSTagger(StandfordCoreNLP): # 词性标注子类
	def __init__(self,jarpath,modelpath):
		StandfordCoreNLP.__init__(self,jarpath):
			self.modelpath = modelpath # 模型文件路径
			self.classfier = "edu.stardford.nlp.tagger.maxent.MaxentTagger" # 词性标注主类
			self.delimiter = "/" #标签分割符
			self.__buildcmd()
	
	def __buildcmd(self): #构建命令行
		self.cmdline = 'java -mxlg -cp "'+self.jarpath+'" '+self.classfier+'-model "'+self.modelpath+'" -tagSeparator '+self.delimiter
	
	def tag(self,sent): # 标注句子
		self.savefile(self.tempsrcpath,sent)
		tagtxt = os.popen(self.cmdline+" -textFile "+self.tempsrcpath,'r').read() #结果输出到变量中
		self.delfile(self.tempsrcpath)
		return tagtxt
	
	def tagfile(self,inputpath,outpath):
		os.system(self.cmdline+' -textFile '+inputpath+' > '+outpath )
		
		
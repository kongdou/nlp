#encoding=utf-8
#stanNLP
import sys
import os

class StanfordCoreNLP():
	def __init__(self,jarpath):
		self.root = jarpath
		self.tempsrcpath = "tempsrc" # temp path
		self.jarlist = ["ejml-0.23.jar","javax.json.jar","jollyday.jar","joda-time.jar","protobuf.jar","slf4j-api.jar","slf4j-simple.jar","standford-corenlp-3.6.0.jar","xom.jar"]
		self.jarpath = ""
		self.buildjars()
	
	def buildjars(self): # bulid all jar package path by root
		for jar in self.jarlist:
			self.jarpath += self.root+jar+";"
	
	def savefile(self,path,sent): # create temp file path
		fp = open(path,"wb")
		fp.write(sent)
		fp.close()
	
	def delfile(self,path):
		os.remove(path)

class StanfordPOSTagger(StanfordCoreNLP):
	def __init__(self,jarpath,modelpath):
		StanfordCoreNLP.__init__(self,jarpath)
		self.modelpath = modelpath  # model file path
		self.classfier = 'edu.stanford.nlp.tagger.maxent.MaxentTagger'
		self.delimiter = '/' #tag split
		self.__buildcmd()
	
	def __buildcmd(self): #bulid cmd
		self.cmdline = 'java -mxlg -cp "'+self.jarpath+'" '+self.classfier+'-model "'+self.modelpath+'" -tagSeparator '+self.delimiter
	
	def tag(self,sent): # tag 
		self.savefile(self.tempsrcpath,sent)
		tagtxt = os.popen(self.cmdline+" -textFile "+self.tempsrcpath,'r').read() #result 
		self.delfile(self.tempsrcpath)
		return tagtxt
	
	def tagfile(self,inputpath,outpath):
		os.system(self.cmdline+' -textFile '+inputpath+' > '+outpath )
		
		
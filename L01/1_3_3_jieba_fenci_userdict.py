#encoding=utf-8
import sys
import os
import jieba

reload(sys)
sys.setdefaultencoding('utf-8')
# 使用自定意分词
jieba.load_userdict("jieba_fenci_userdict.txt")
mess = '三者险30万，车损险100万'
#结巴分词，全模式
wordlist = jieba.cut(mess,cut_all=True) 
print (" | ".join(wordlist))
#结巴分词，精确切分
wordlist = jieba.cut(mess)
print (" | ".join(wordlist))
#结巴分词，搜索引擎模式
wordlist = jieba.cut_for_search(mess)
print (" | ".join(wordlist))

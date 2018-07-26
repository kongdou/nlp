#encoding=utf-8
import sys
import os
import jieba
import importlib

importlib.reload(sys)
#sys.setdefaultencoding('utf-8')
# 使用自定意分词
jieba.load_userdict("jieba_fenci_userdict.txt")
sent = '在包含问题的所有解的解空间树中，按照深度优化搜素的策略，从根节点出发深度探索解空间树。'
mess = '三者险30万，车损险险100万'
wordlist = jieba.cut(mess,cut_all=True)
print (" | ".join(wordlist))
wordlist = jieba.cut(mess)
print (" | ".join(wordlist))
wordlist = jieba.cut_for_search(mess)
print (" | ".join(wordlist))
 
 
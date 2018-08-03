# -*- encoding: utf-8 -*-

import sys
import os
import code
from pyltp import *
reload(sys)

sys.setdefaultencoding('utf-8')

sent = '欧洲 东部 的 罗马尼亚 ， 首都 是 布加勒斯特 ，也 是 一座 世界性 的 城市。'
words = sent.split(' ')

postagger = Postagger()
postagger.load('E:\\nlp\\ltp-data-v3.3.1\\pos.model')
postags = postagger.postag(words)

recognizer = NamedEntityRecognizer()
recognizer.load('E:\\nlp\\ltp-data-v3.3.1\\ner.model')
netags = recognizer.recognize(words,postags)

for word,postag,netag in zip(words,postags,netags):
	print code.coder(word+"/"+postag+"/"+netag),
	
#结果：
#欧洲/ns/S-Ns 东部/nd/O 的/u/O 罗马尼亚/ns/S-Ns ，/wp/O 首都/n/O 是/v/O 布加勒斯特/ns/S-Ns ，也/d/O 是/v/O 一座/m/O 世界性/n/O 的u/O 城市。/n/O
# O表示非专名词，S-Ns为识别的专名



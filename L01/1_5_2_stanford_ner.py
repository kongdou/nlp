# -*- encoding: utf-8 -*-

import sys
import os
import code

from stanford import StanfordNERTagger

reload(sys)
sys.setdefaultencoding('utf-8')

root = 'E:/nlp/nltk_data/stanford-corenlp/'
modelpath = root + 'models/ner/chinese.misc.distsim.crf.ser.gz'

st = StanfordNERTagger(modelpath,root)
seg_sent = '欧洲 东部 的 罗马尼亚 ，首都 是 布加勒斯特，也 是 一 座 世界性 的城市。'
taglist =st.tagfile(seg_sent,"ner_test.txt")
print taglist
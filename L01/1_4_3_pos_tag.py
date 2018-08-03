# -*- coding: utf-8 -*-

import sys
import code
from stanford import StanfordPOSTagger

reload(sys)
sys.setdefaultencoding('utf-8')
root = 'E:\\nlp\\nltk_data\\stanford-corenlp\\'
modelpath = root + 'models\\pos-tagger\\chinese-distsim\\chinese-distsim.tagger'
st = StanfordPOSTagger(root,modelpath)
seg_sent = '在 包含 问题 的 所有 解 的 解空间树 中 ，按照 深度优化 搜索 的 策略 ， 从 根节点 出发 深度 探索 解空间树'
taglist = st.tag(seg_sent)
print code.coder(taglist)
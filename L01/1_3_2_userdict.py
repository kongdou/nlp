#encoding=utf-8
import sys
import os
import code #导入自定义
from pyltp import Segmentor #导入ltp库
reload(sys)
sys.setdefaultencoding('utf-8')

model_path = "E:\\nlp\\ltp-data-v3.3.1\\cws.model" #ltp 3.3分词库模型
user_dict = "E:\\nlp\\ltp-data-v3.3.1\\fulluserdict.txt"  #外部专有名词词典路径
segmentor = Segmentor()

#segmentor.load(model_path)
segmentor.load_with_lexicon(model_path,user_dict)#加载用户自定义专有名词词典
sent = '在包含问题的所有解的解空间树中，按照深度优化搜素的策略，从根节点出发深度探索解空间树。'
words = segmentor.segment(sent)
print code.vectrotostr(words,' | ')
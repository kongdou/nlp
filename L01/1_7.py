# -*- coding: utf-8 -*-
import sys
import os
import code
from pyltp import *

reload(sys)
sys.setdefaultencoding('utf-8')

MODELDIR = 'E:/nlp/ltp-data-v3.3.1'
sentence = '欧洲东部的罗马尼亚，首都是布加勒斯特，也是一座世界性的城市。'

# 分词
segmentor = Segmentor()
segmentor.load(os.path.join(MODELDIR,'cws.model'))
words = segmentor.segment(sentence)

# 词性标注
wordlist = list(words)
postagger = Postagger()
postagger.load(os.path.join(MODELDIR,'pos.model'))
postags = postagger.postag(words)

# 对分词和词性标注结果加入到分析器中进行句法解析
parser = Parser()
parser.load(os.path.join(MODELDIR,'parser.model'))
arcs = parser.parse(words,postags)

# 实体命名识别
recognizer = NamedEntityRecognizer()
recognizer.load(os.path.join(MODELDIR,'ner.model'))
netags = recognizer.recognize(words,postags)

# 语义角色标注
labeller = SementicRoleLabeller()
labeller.load(os.path.join(MODELDIR,'srl'))
roles = labeller.label(words,postags,netags,arcs)

# UTF-8转gbk，cmd控制台显示
wordlist_ = code.coderforlist(words);

for role in roles:
	print 'rel:',wordlist_[role.index] # 谓词
	for arg in role.arguments:
		if arg.range.start != arg.range.end:
			print code.coder(arg.name),' '.join(wordlist_[arg.range.start:arg.range.end])
		else:
			print code.coder(arg.name),wordlist_[arg.range.start]
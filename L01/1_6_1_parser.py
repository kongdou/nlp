# -*- encoding: utf-8 -*-

import sys
import os
import code
import nltk

from nltk.tree import *
from nltk.grammar import DependencyGrammar
from nltk.parse import *
from pyltp import *
import re

reload(sys)
sys.setdefaultencoding('utf-8')

sent = '罗马尼亚 的 首都 是 布加勒斯特 。'
words = sent.split(' ')

postagger = Postagger()
postagger.load('E:\\nlp\\ltp-data-v3.3.1\\pos.model')
postags = postagger.postag(words)

parser = Parser()
parser.load("E:\\nlp\\ltp-data-v3.3.1\\parser.model")
arcs = parser.parse(words,postags)

arclen = len(arcs)
conll = ''

for i in xrange(arclen):
	if arcs[i].head == 0:
		arcs[i].relation = 'ROOT'
	conll+='\t'+words[i]+'('+postags[i]+')'+'\t'+postags[i]+'\t'+str(arcs[i].head)+'\t'+arcs[i].relation+'\n'
 	
print code.coder(conll)
conlltree = DependencyGraph(conll)
tree = conlltree.tree()
tree.draw()

#encoding=utf-8
import sys
import jieba

from pyltp import *

sent = '在 包含 问题 的 所有 解 的 解空间树 中'
words = sent.split(" ")
postagger = Postagger()
postagger.load("")

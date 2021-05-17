from gensim.summarization.summarizer import summarize
from gensim.summarization import keywords
from textblob import TextBlob
def main(input):

    mystr=input
    sum=""
    summ_words = summarize(mystr,word_count=500)
    l = []
    for i in range(len(summ_words)):
        l.append(summ_words[i])
    l2 = l
    seperator=""
    final_str = seperator.join(l)
    final_str1 = final_str.split(".")
    for i in range(len(final_str1)):
        final_str1[i]=final_str1[i]+'.'
        sum = sum+final_str1[i]

    op = ""
    senlist = []
    sen = sum.split("\n")
    for  i in range(len(sen)):
        a = TextBlob(sen[i])
        senlist.append(a.sentiment)
    for i in range(len(senlist)):
        if((senlist[i].polarity)<0.0):
            op = op+sen[i]+"\n";
    return ""+op
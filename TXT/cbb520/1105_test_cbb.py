#include "Python.h"
# Taking your set of longest, conserved, unique gene sequences you found above, 
# write a program to test the quality of an S. cerevisiae genome assembly.



####################################################################################################
#create an object of gene;
####################################################################################################
class gene:
    def __init__(self, name, sequence, length):
        self.n = name
        self.s = sequence
        self.l = length
    def getName(self):
        return self.n
    def setName(self, name):
        self.n = name
    def getSequence(self):
        return self.s
    def setSequence(self, sequence):
        self.s = sequence
    def getLength(self):
        return self.l
    def setLength(self, length):
        self.l = length




####################################################################################################
#create an object of contig:
####################################################################################################
class contig:
    def __init__(self, name, sequence, length):
        self.n = name
        self.s = sequence
        self.l = length
    def getName(self):
        return self.n
    def setName(self, name):
        self.n = name
    def getSequence(self):
        return self.s
    def setSequence(self, sequence):
        self.s = sequence
    def getLength(self):
        return self.l
    def setLength(self, length):
        self.l = length



####################################################################################################
#align each gene (from geneList) to whole contigs in contigList;
#whenever we got a fraction in some contig has >50% identical, we got a "Match"
####################################################################################################
def scoreContigList(geneList, contigList):
    numOfGene = len(geneList)

    for i in range(0, numOfGene, 1):
        print("i: " + str(i) + geneList[i].getName())
        scoreGene(geneList[i], contigList)




####################################################################################################
#align a single gene with the whold contigs;
####################################################################################################
def scoreGene(gene, contigList):
    numOfContigs = len(contigList)
    score = 0

    for j in range(0, numOfContigs):
        currContig = contigList[j].getSequence();
        if compareTwoSeq(gene.getSequence(), currContig):
        #    print("Score: " + str(score))
            score = score +1
            
    print("Score: " + str(score) + " Gene: " + str(gene.getName()))

    


####################################################################################################
#compare two sequences:
    #the contig-sequence should be much longer than the gene sequence;
    #each time, cut-off a short sequence from the contig with the same length as the gene sequence;
    #call Over50Identical() method to check whether there's "match" or not;
####################################################################################################
def compareTwoSeq(geneSeq, contigSeq):
    contigLen = len(contigSeq)
    geneLen = len(geneSeq)
    start = 0
    end = contigLen - geneLen +1
    revgeneSeq = ""

    #get the reverse sequence of current gene:
    for k in range(0, geneLen):
        revgeneSeq = geneSeq[k] + revgeneSeq 

    #get sequence fraction from contig:
    for l in range(start, end, 1):
        #print("start:" + str(l) + "  end: " + str(end) +" length: " )
        subSeqOfContig = contigSeq[l: l + geneLen]          ####here, the sub-sequence should be str(l) to str(l+geneLen);
        if(Over50Identical(subSeqOfContig, geneSeq)):
            #start = start + 1
            print("original gene works! true dammit")
            return True
        if(Over50Identical(subSeqOfContig, revgeneSeq)):
            #start = start + 1
            print("reverse gene works!")
            return True
    #print("return or else!")
    return False



####################################################################################################
#check if two sequences have more than 50% identical blocks;
####################################################################################################

def Over50Identical(str1, str2):
   # print("str1: " + str1 + "str2: " + str2)
    Len = len(str1)
   # print(str(Len))
    
    ident = 0
    for m in range(0, Len, 1):
        if(str1[m] == str2[m]):
           # print(str(ident))
            ident = ident + 1

    if(ident >= Len/2):
        #print("true")
        return True

    #print("false")                #not necessary to print "false everytime", there are millions of comparations;
    return False



####################################################################################################
#create contigArrayList method;
####################################################################################################
def createContigArrayList():

    print("Step 2: read contigs from abyss/velvet")
    new_contig = contig("", "", 0)
    contigList = []

    #add file here from resulting contig list (velvet/abyss)
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\abyss_k43_contigs.fa', 'r') as genesReader:
    #with open('http://alchemy.duhs.duke.edu/~gd44/cbb520/velvet_contigs.fa', 'r') as genesReader:
    
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_contig.setLength(len(new_contig.getSequence()))
                if(new_contig.getLength() > 1000):
                    contigList.append(new_contig)
                new_contig = contig(currStr, "", 0)
            else:
                seq = new_contig.getSequence() + currStr
                new_contig.setSequence(seq)
    print("Contigs (>1000 bp): " + str(len(contigList)))

    #for p in range(0, len(contigList), 1):
       # print("name: " + str(contigList[p].getName()) + " size: " + str(contigList[p].getLength()))
    return contigList



####################################################################################################
#create geneArrayList method:
####################################################################################################
def createGeneArrayList():

    print("Step 1: read genes")
    new_gene = gene("", "", 0)
    geneList = []

    #add file here from resulting contig list (velvet/abyss)
    #D:\2014 Fall Semester\CBB 520 Course\Homeworks\Assignment 3
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\seqdump.txt', 'r') as genesReader:
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_gene.setLength(len(new_gene.getSequence()))
                #if(new_gene.getLength() > 10):
                geneList.append(new_gene)
                new_gene = gene(currStr, "", 0)
            else:
                seq = new_gene.getSequence() + currStr
                new_gene.setSequence(seq)
    print("Genes: " + str(len(geneList)))

    #for p in range(0, len(geneList), 1):
      #  print("name: " + str(geneList[p].getName()) + " size: " + str(geneList[p].getLength()))
    return geneList




####################################################################################################
# main() function;
####################################################################################################
def main():
    geneList = createGeneArrayList()
    contigList = createContigArrayList()
    
    
    scoreContigList(geneList, contigList)

if __name__ == "__main__":
    main()
        

    
####################################################################################################
#END
####################################################################################################

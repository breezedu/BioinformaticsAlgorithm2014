#######################################################
#CBB520 HomeWork2 Part4

#import operation system
import subprocess as sp
import os, sys



###########################################################################

class results:
    def __init__(self, chromose, pos, ref, alt):
        self.chr = chromose
        self.pos = pos
        self.ref = ref
        self.alt = alt
        
    def getChrom(self):
        return self.chr
    def setChrom(self, chromose):
        self.chromose = chromose
        
    def getPosition(self):
        return self.pos
    def setPosition(self, position):
        self.pos = position
        
    def getRef(self):
        return self.ref
    def setRef(self, ref):
        self.ref = ref
        
    def getAlt(self):
        return self.alt
    def setAlt(self, alt):
        self.alt = alt



###########################################################################
# create contig objects
###########################################################################

class contig:
    def __init__(self, name, sequence, length):
        self.name = name
        self.sequence = sequence
        self.length = length
        
    def getName(self):
        return self.name
    def setName(self, name):
        self.name = name
        
    def getSequence(self):
        return self.sequence
    def setSequence(self, sequence):
        self.sequence = sequence
        
    def getLength(self):
        return self.length
    def setLength(self, length):
        self.length = length



###########################################################################
# create an ArrayList of vcf-objects
###########################################################################

def createVcfList():
    print("Step 1: make an arrayList of vcf reads from *.vcf file: ")

    # create an arrayList to store all vcf objects
    vcfList = []

    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\ver6h_var2.txt') as genesReader:

    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\abyss_var2.txt') as genesReader:

    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\velvet_var2.txt') as genesReader:
        lines = genesReader.read()
        values = lines.split('\n')

        for n in range(2, len(values)-2, 1):
            temp = []
            temp = values[n].split()

            print("vcf_name: " + temp[0] + " pos:" + temp[1] + " ref:" +temp[3] + " alt:" + temp[4])
            chrom = temp[0]
            pos = int(temp[1])
            ref = temp[3]
            alt = temp[4]

            print(chrom + "\t" + str(pos) + "\t" + ref + "\t" + alt)

            if(len(temp[0]) > 25 & len(temp[3]) < 2):
                # create a new result object, put it into vcfList arrayList;
                nv = results(chrom, pos, ref, alt)
                vcfList.append(nv)

        print("LEN of vcfList: " + str(len(vcfList))        )
    return vcfList



###########################################################################

def createContigList():
    print("Step 2: make a contig array list: ")
    new_contig = contig("", "", 0)
    contigList = []

    #read file here from resulting contig list (velvet/abyss/ver6h)
    
    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\abyss_contigs.fa', 'r') as genesReader:
    
    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\ver6h_contigs.fa', 'r') as genesReader:
    
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\velvet_contigs.fa', 'r') as genesReader:
        lines = genesReader.read().splitlines()
        
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_contig.setLength(len(new_contig.getSequence()))
                if(new_contig.getLength() > 100):
                    contigList.append(new_contig)
                    
                new_contig = contig(currStr[1:], "", 0)
            else:
                seq = new_contig.getSequence() + currStr
                new_contig.setSequence(seq)
                
    print("Contigs (>100 bp): " + str(len(contigList)))

    for p in range(0, len(contigList), 1):
       # print("contig name: " + str(contigList[p].getName()) + "   size: " + str(contigList[p].getLength()))

    return contigList


####################################################################################################
def checkSNPs(vcfList, contigList):
    print("Step 3: find SNPs sequences: " )
    for n in range(0, len(vcfList)-1, 1):
        currVcf = vcfList[n]

        vcfName = currVcf.getChrom()
        vcfPosition = currVcf.getPosition()-1
        vcfRef = currVcf.getRef()
        vcfAlt = currVcf.getAlt()
        
        print("VCF Name: " + str(vName))
        
        for m in range(0, len(contigList), 1):
            currContig = contigList[m]
            temp = currContig.getName()
            cName = temp
            #print("Name2:" + cName)

            #############################
            # locate the specific contigs where the SNPs happens;
            if(vcfName == cName):
                temp = currContig.getSequence()
                oriSeq = temp[vcfPosition-10:vcfPosition+11]
                pos = temp[vcfPosition]
                pre = temp[vcfPosition-10:vcfPosition]
                post = temp[vcfPosition+1:vcfPosition+11]

                print("Contig: \t" + vcfName)
                print("original seq: \t " + oriSeq)
                print("contig is: \t" + pre + " " + vcfAlt + " " + post)
                print("reference is: \t" + pre + " " + vcfRef + " " + post)


###########################################################################
#main()
###########################################################################

def main():
    #get vcf object arrayList;
    vcfList = createVcfList()

    #get congit object arrayList;
    contigList = createContigList()

    #check the SNPs in reference/contig arrayList;
    checkSNPs(vcfList, contigList)
    
    print("total SNP's: " + str(len(vcf)))

if __name__ == "__main__":
    main()
    
###########################################################################

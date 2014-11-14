import subprocess as sp
import os, sys
 
# call samtools to run in Python
# command line format: samtools view -bS -o filename.bam filename.sam
# where the .bam file is a new file in the compressed BAM format, and the .sam file
# is the file we just made above
 
 
program = 'samtools'
 
# the directory to store all sam files:
# /home/gd44/cbb520/SamFiles/
samdir = '/home/gd44/cbb520/SamFiles/'
samfiles = os.listdir('/home/gd44/cbb520/SamFiles/')
print (samfiles)
 
# the directory to store bam files:
# /home/gd44/cbb520/BamFiles/

bamdir = '/home/gd44/cbb520/BamFiles/'
try:
    os.mkdir(bamdir)
except Exception as error:
    print (error)
    print ("WARNING:  BAMDIR already exists!")
 
 
 
for samfile in samfiles:
    # set the sam and bam file names
    print(bamdir)
    print (samfile)
    bamfile = bamdir + samfile.split('/')[-1].replace('.sam', '.bam')
    samfile = samdir + samfile
    print (samfile)
    print (bamfile)
 
    #########
    #transfer SAM to BAM files:
    proc=sp.Popen( [program, 'view', '-h', '-o', bamfile, samfile] )
 
print("SAM to BAM ready!!!!!")
#####################
#bam to sorted bam

bamdir = '/home/gd44/cbb520/BamFiles/'
bamfiles = os.listdir('/home/gd44/cbb520/BamFiles/')
print bamfiles
 
sorteddir = '/home/gd44/cbb520/BamFilesSorted/'
try:
    os.mkdir(sorteddir)
except Exception as error:
    print (error)
    print ("WARNING:  SORTEDDIR already exists!")
 
#############
    #Everthing works fine so far;;
    
for bamfile in bamfiles:
    # set the sam and bam file names
    print("sort bamfiles 2:")
    print(bamfile)
    sortedfile = sorteddir + bamfile.split('/')[-1].replace('.bam', '.sorted')
    bamfile = bamdir + bamfile
    print(bamfile)
    print(sortedfile)
 
    ############
    #sort bam files:
    proc=sp.Popen( [program, 'sort', bamfile, sortedfile] )
 

 

import subprocess as sp
import os, sys
 
# call samtools 
# command line format: samtools view -bS -o filename.bam filename.sam
# bam file is a new file in a BAM format 
 
program = 'samtools'
 
# the directory to store all sam files:
samdir = '/home/bitnami/Desktop/SamFiles/'
samfiles = os.listdir('/home/bitnami/Desktop/SamFiles/')
print (samfiles)
 
# the directory to store bam files:
bamdir = '/home/bitnami/Desktop/BamFiles/'

try:
    os.mkdir(bamdir)
except Exception as error:
    print (error)
    print ("Error:  BAMDIR already exists!")
 
 
for samfile in samfiles:
    # set the sam and bam file names
    print(bamdir)
    print (samfile)
    bamfile = bamdir + samfile.split('/')[-1].replace('.sam', '.bam')
    samfile = samdir + samfile
    print (samfile)
    print (bamfile)
 
    #transfer SAM to BAM files:
    proc=sp.Popen( [program, 'view', '-h', '-o', bamfile, samfile] )
 
print("------------Next-------------")

#sort bam

bamdir = '/home/bitnami/Desktop/BamFiles/'
bamfiles = os.listdir('/home/bitnami/Desktop/BamFiles/')
print (bamfiles)
 
sorteddir = '/home/bitnami/Desktop/BamFilesSorted/'
try:
    os.mkdir(sorteddir)
except Exception as error:
    print (error)
    print ("Error: already exists!")
 
######################################################
    
for bamfile in bamfiles:
    # set the sam and bam file names
    print("sort bamfiles:")
    print(bamfile)
    sortedfile = sorteddir + bamfile.split('/')[-1].replace('.bam', '.sorted')
    bamfile = bamdir + bamfile
    print(bamfile)
    print(sortedfile)
 
    #sort bam files:
    proc=sp.Popen( [program, 'sort', bamfile, sortedfile] )
 

 

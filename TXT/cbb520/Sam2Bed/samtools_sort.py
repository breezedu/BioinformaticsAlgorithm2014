import subprocess as sp
import os, sys
 
 
# now for samtools
# the format of the command line we're trying to mimick is:
# samtools view -bS -o filename.bam filename.sam
# where the .bam file is a new file in the compressed BAM format, and the .sam file
# is the file we just made above
 
program = 'samtools'
 
bamdir = '/home/gd44/cbb520/BamFiles/'

bamfiles = os.listdir('/home/gd44/cbb520/BamFiles/')
print (bamfiles)
 
sorteddir = '/home/gd44/cbb520/BamFilesSorted/'
try:
    os.mkdir(sorteddir)
except Exception as error:
    print (error)
    print ("WARNING:  SORTEDDIR already exists!")
 
 
for bamfile in bamfiles:
    # set the sam and bam file names
    print (bamfile)
    sortedfile = sorteddir + bamfile.split('/')[-1].replace('.bam', '.sorted')
    bamfile = bamdir + bamfile
    print (bamfile)
    print (sortedfile)
 
    proc=sp.Popen( [program, 'sort', bamfile, sortedfile] )
 

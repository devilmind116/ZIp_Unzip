using System;
using System.IO;
using System.IO.Compression;

namespace ZIp_Unzip
{
    class Program
    {
        static void Main(string[] args)
        {
            ZipIt();
            retentionProcess();
        }

        

    public static void ZipIt()
        {

            string srcpath = "A:\\Study Materials";
            string targetpath = "A:\\Study Materials";
            DirectoryInfo dr = new DirectoryInfo(srcpath);

            ZipFile.CreateFromDirectory(srcpath, targetpath);
            bool zipped = true;
            if (zipped)
            {
                foreach (FileInfo file in dr.EnumerateFiles())
                {
                    file.Delete();
                }
            }

        }

        public static void retentionProcess()
        {
            string srcpath = "A:\\Study Materials";

            DirectoryInfo d = new DirectoryInfo(srcpath);

            if (d.CreationTime < DateTime.Now.AddDays(-7))
            {
                d.Delete();
            }
                
        }
    }
}

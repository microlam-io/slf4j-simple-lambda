module org.slf4j.simple { 
  requires org.slf4j;
  provides org.slf4j.spi.SLF4JServiceProvider with io.microlam.slf4j.simple.SimpleServiceProvider;
}

## Oracle Java is supported on CentOS 6

class { 'jdk_oracle':
    version => '8',
    version_update => '101',
    version_build => '13',
    platform => 'x64',
    ensure => 'installed'
}

notify {"Finished puppet script!":}
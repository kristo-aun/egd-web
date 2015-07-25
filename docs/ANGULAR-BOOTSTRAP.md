# Angular Bootstrap tips

Build ui-bootstrap-tpls.js from snapshot
====
    git clone https://github.com/angular-ui/bootstrap.git
    cd bootstrap
    npm install
    grunt html2js
    grunt build
    
You can now pick up the file  - dist/ui-bootstrap-tpls-x.x.x-SNAPSHOT.js.
Don't forget to commit the file using a different name from previous ui-bootstrap. 
Otherwise your custom changes will become very hard to track later on.

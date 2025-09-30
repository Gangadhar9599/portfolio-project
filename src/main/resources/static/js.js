// This script relies on being placed just before the closing </body> tag.

document.getElementById('year').textContent = new Date().getFullYear();

const contactForm = document.getElementById('contact-form');
const formStatus = document.getElementById('form-status');
const sendButton = contactForm ? contactForm.querySelector('button') : null;

let isSubmitting = false;

// Use the form's reliable 'submit' event listener
if (contactForm && sendButton) {
    contactForm.addEventListener('submit', async (e) => {
        e.preventDefault(); // CRITICAL: Stop the browser from refreshing the page

        // Check submission status first to prevent double emails
        if (isSubmitting) {
            return;
        }

        // isSubmitting is set, and button is disabled
        isSubmitting = true;
        sendButton.disabled = true;

        formStatus.textContent = 'Sending...';
        formStatus.style.color = 'var(--text-light)';

        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            message: document.getElementById('message').value,
        };

        let response = null;

        try {
            response = await fetch('/api/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                formStatus.textContent = 'Message sent successfully! Thank you.';
                formStatus.style.color = '#415a77';
                contactForm.reset();
            } else {
                // Server responded with an error status (4xx or 5xx)
                throw new Error('Server responded with an error.');
            }
        } catch (error) {
            console.error('Form submission error:', error);
            formStatus.textContent = 'An error occurred. Please try again.';
            formStatus.style.color = '#ff4f4f';

            // Reset state on failure
            sendButton.disabled = false;
            isSubmitting = false;
        }
    });
}